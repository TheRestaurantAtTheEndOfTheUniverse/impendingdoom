(ns converis-lint.data.choicegroups)

(defn- tree-cgv [root cgvs]
  (let [children (filter #(= (:name root) (:parentChoiceGroupValue %1)) cgvs)]
    ;;(.log js/console (str "children of " (:name root) ": " (map :name children)))
    (if (empty? children)
      (select-keys root [:name :selectable :step] )
      (assoc root :children (map #(tree-cgv %1 cgvs) 
                                 (sort-by :step children))))))

(defn- tree-cgvs [cgvs]
  (let [roots (filter #(not (:parentChoiceGroupValue %1)) cgvs)]
    ;;(.log js/console (str "roots: " roots))
    (map #(tree-cgv %1 cgvs) roots)))

(defn convert-choicegroup[cg]
  (if (:tree cg)
    (assoc (select-keys cg [:name :tree]) :choice-group-values
           (tree-cgvs (:choiceGroupValues cg)))
    (assoc (select-keys cg [:name :tree]) :choice-group-values
           (:choiceGroupValues cg))))


(def choicegroups 
  [ {
     :name "LINKED_SIDE_OF_RELATIONTYPE"
     :tree false
     :choiceGroupValues [ {
                           :name "LEFT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "NOT APPLICABLE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "RIGHT"
                           :selectable true
                           :step 0
                           } ]
     }
    {
     :name "STATISTICS_TYPE"
     :tree false
     :choiceGroupValues [ {
                           :name "OVERVIEW"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "TIME"
                           :selectable true
                           :step 0
                           } ]
     }
    {
     :name "CLICK_TYPE"
     :tree false
     :choiceGroupValues [ {
                           :name "detail"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "infobox"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "pdf"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "print"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "tellafriend"
                           :selectable true
                           :step 0
                           } ]
     }
    {
     :name "STATIC_CONTENT"
     :tree false
     :choiceGroupValues [ {
                           :name "ldif_import_status_email_message"
                           :selectable true
                           :step 100
                           }
                          {
                           :name "Publications waiting for validation"
                           :selectable true
                           :step 72
                           }
                          {
                           :name "Publikation(en) zu validieren."
                           :selectable true
                           :step 1
                           }
                          {
                           :name "raindance_email_message"
                           :selectable true
                           :step 100
                           }
                          {
                           :name "stat_annualreport_announce_subject"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_annualreport_email_from"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_annualreport_name_from"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_annualreport_reminder_subject"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_auto_body_publ_search"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_auto_body_publ_status_change_author"
                           :selectable true
                           :step 53
                           }
                          {
                           :name "stat_auto_body_publ_status_change_organisation_admin"
                           :selectable true
                           :step 52
                           }
                          {
                           :name "stat_auto_email_avedas"
                           :selectable true
                           :step 60
                           }
                          {
                           :name "stat_auto_subj_publ_search"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_auto_subj_publ_status_change_author"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "stat_auto_subj_publ_status_change_organisation_admin"
                           :selectable true
                           :step 51
                           }
                          {
                           :name "stat_contact"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_email_delegation_body"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_email_delegation_subj"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_help"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_imprint"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_admin_search"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_all_static_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_annual_report_approval"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_annual_report_parameters"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_annual_report_status"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_bulk_relate"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_cgv_map"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_citation_retrieval"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "stat_module_content_statistics"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_contract_report"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_cv_generator"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_data_export"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_data_import"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_auto_message_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_help_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_hint_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_module_help_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_selection_list_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_static_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_edit_user_management"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_file_upload"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_generate_autorelations"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_io_filter_for_relations"
                           :selectable true
                           :step 41
                           }
                          {
                           :name "stat_module_ir_configuration"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_keyword_search"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_link_checker"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_auto_message_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_generator"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_help_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_hint_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_module_help_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_selection_list_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_static_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_list_user_management"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_my_preferences"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_network_graph_generator"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_auto_message_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_help_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_hint_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_module_help_text_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_selection_list_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_newsletter_compile"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_newsletter_selecttemplates"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_static_content_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_new_user_management"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_AAA_AAA_AA"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_ACADEMIC_EDUCATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_ACTIVITY"
                           :selectable true
                           :step 67
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_ADMISSION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_APPARATUR"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_APPLICATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_APPOINTMENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_APPOINTMENTS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_AREA"
                           :selectable true
                           :step 69
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_AWARD"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_BEWERTUNG"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CARD"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CENTER"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CFEVENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CFLANG"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CFLANGSKILL"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CFPERS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CITATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CLASSIFICATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_COMPETENCE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_COMPETENCE_FIELD"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CONTRACT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_COST"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_COUNTRY"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_CV_ACTIVITY"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_DISSERTATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_DOCTORATE_COURSE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EMBEDDED_FILE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EMPHASIS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EQUIPMENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EXECUTING_ORGANISATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EXPERTISE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EXTERNAL_FUNCTION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EXTERNAL_ORGANISATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_EXTFUNCCHK"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FILE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FIXEDMILESTONE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FOCUS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FOCUSES"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FOERDERPROGRAMM"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FUNDING_INSTRUMENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FUNDING_PER_YEAR"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FUNDING_SOURCE"
                           :selectable true
                           :step 70
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FUNDING_TYPE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_FUPY"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_GRADE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_GRADUATE_PROGRAMS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_GRADUATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_GRANT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_HABILITATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_IDEA"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_ISISUBJCAT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_ISI_SUBJECT_CATEGORY"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_JOURNAL"
                           :selectable true
                           :step 71
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_LANGUAGE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_MEETING"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_MESSAGE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_MILESTONE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_ORGANISATION"
                           :selectable true
                           :step 68
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PATENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PEERREVIEW"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PEER_REVIEW"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PERSON"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PERSTEST"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PICTURE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_POSITION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PRICE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PROGRAM"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PROGRESS_EXCEPTION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PROJECT"
                           :selectable true
                           :step 65
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PROJECT_APPLICATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PROJECT_EXECUTING_ORGANISATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PSP_ELEMENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PUBLICATION"
                           :selectable true
                           :step 66
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PUBLICATION_APPLICATION"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_PUBLTEST"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_RATING"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_RDFDOMAIN"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_REFCLAIM"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_REF_CLAIM"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_RESEARCH_FOCUS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_SEARCH_PROFILE"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_STUDY_PLAN"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_TAGS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_TASK"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_UNITOFASSESS"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_UNITOFASSESSMENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_UNIT_OF_ASSESSMENT"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_objects_list_view_IOT_USER_PHOTO"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_project_report_filter"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_publication_confirm"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_publication_import"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_publication_report_filter"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_publication_search"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_report_filter"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_role_switch"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_start_page_objects"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_tree_editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_usage_statistics"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_module_user_delegation"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_msgbody_tellafriend"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_terms"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "stat_welcome"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Things to do"
                           :selectable true
                           :step 55
                           } ]
     }
    {
     :name "country"
     :tree false
     :choiceGroupValues [ {
                           :name "Afghanistan"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Åland Islands"
                           :selectable true
                           :step 244
                           }
                          {
                           :name "Albania"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Algeria"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Andorra"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Angola"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Anguilla"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Antarctica"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Antigua And Barbuda"
                           :selectable true
                           :step 9
                           }
                          {
                           :name "Argentina"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Armenia"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "Aruba"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "Australia"
                           :selectable true
                           :step 13
                           }
                          {
                           :name "Austria"
                           :selectable true
                           :step 14
                           }
                          {
                           :name "Azerbaijan"
                           :selectable true
                           :step 15
                           }
                          {
                           :name "Bahamas"
                           :selectable true
                           :step 16
                           }
                          {
                           :name "Bahrain"
                           :selectable true
                           :step 17
                           }
                          {
                           :name "Bangladesh"
                           :selectable true
                           :step 18
                           }
                          {
                           :name "Barbados"
                           :selectable true
                           :step 19
                           }
                          {
                           :name "Belarus"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Belgium"
                           :selectable true
                           :step 21
                           }
                          {
                           :name "Belize"
                           :selectable true
                           :step 22
                           }
                          {
                           :name "Benin"
                           :selectable true
                           :step 23
                           }
                          {
                           :name "Bermuda"
                           :selectable true
                           :step 24
                           }
                          {
                           :name "Bhutan"
                           :selectable true
                           :step 25
                           }
                          {
                           :name "Bolivia"
                           :selectable true
                           :step 26
                           }
                          {
                           :name "Bosnia And Herzegovina"
                           :selectable true
                           :step 27
                           }
                          {
                           :name "Botswana"
                           :selectable true
                           :step 28
                           }
                          {
                           :name "Brazil"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Brunei Darussalam"
                           :selectable true
                           :step 32
                           }
                          {
                           :name "Bulgaria"
                           :selectable true
                           :step 33
                           }
                          {
                           :name "Burkina Faso"
                           :selectable true
                           :step 34
                           }
                          {
                           :name "Burundi"
                           :selectable true
                           :step 35
                           }
                          {
                           :name "Cambodia"
                           :selectable true
                           :step 36
                           }
                          {
                           :name "Cameroon"
                           :selectable true
                           :step 37
                           }
                          {
                           :name "Canada"
                           :selectable true
                           :step 38
                           }
                          {
                           :name "Cape Verde"
                           :selectable true
                           :step 39
                           }
                          {
                           :name "Cayman Islands"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Central African Republic"
                           :selectable true
                           :step 41
                           }
                          {
                           :name "Chad"
                           :selectable true
                           :step 42
                           }
                          {
                           :name "Chile"
                           :selectable true
                           :step 43
                           }
                          {
                           :name "China"
                           :selectable true
                           :step 44
                           }
                          {
                           :name "Colombia"
                           :selectable true
                           :step 47
                           }
                          {
                           :name "Comoros"
                           :selectable true
                           :step 48
                           }
                          {
                           :name "Congo"
                           :selectable true
                           :step 49
                           }
                          {
                           :name "Cook Islands"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Costa Rica"
                           :selectable true
                           :step 51
                           }
                          {
                           :name "Côte D'Ivoire"
                           :selectable true
                           :step 56
                           }
                          {
                           :name "Croatia"
                           :selectable true
                           :step 52
                           }
                          {
                           :name "Cuba"
                           :selectable true
                           :step 53
                           }
                          {
                           :name "Cyprus"
                           :selectable true
                           :step 54
                           }
                          {
                           :name "Czech Republic"
                           :selectable true
                           :step 55
                           }
                          {
                           :name "Denmark"
                           :selectable true
                           :step 57
                           }
                          {
                           :name "Djibouti"
                           :selectable true
                           :step 58
                           }
                          {
                           :name "Dominica"
                           :selectable true
                           :step 59
                           }
                          {
                           :name "Dominican Republic"
                           :selectable true
                           :step 60
                           }
                          {
                           :name "Ecuador"
                           :selectable true
                           :step 61
                           }
                          {
                           :name "Egypt"
                           :selectable true
                           :step 62
                           }
                          {
                           :name "El Salvador"
                           :selectable true
                           :step 63
                           }
                          {
                           :name "Equatorial Guinea"
                           :selectable true
                           :step 64
                           }
                          {
                           :name "Eritrea"
                           :selectable true
                           :step 65
                           }
                          {
                           :name "Estonia"
                           :selectable true
                           :step 66
                           }
                          {
                           :name "Ethiopia"
                           :selectable true
                           :step 67
                           }
                          {
                           :name "Falkland Islands (Malvinas)"
                           :selectable true
                           :step 68
                           }
                          {
                           :name "Faroe Islands"
                           :selectable true
                           :step 69
                           }
                          {
                           :name "Fiji"
                           :selectable true
                           :step 70
                           }
                          {
                           :name "Finland"
                           :selectable true
                           :step 71
                           }
                          {
                           :name "France"
                           :selectable true
                           :step 72
                           }
                          {
                           :name "French Guiana"
                           :selectable true
                           :step 73
                           }
                          {
                           :name "French Polynesia"
                           :selectable true
                           :step 74
                           }
                          {
                           :name "Gabon"
                           :selectable true
                           :step 76
                           }
                          {
                           :name "Gambia"
                           :selectable true
                           :step 77
                           }
                          {
                           :name "Georgia"
                           :selectable true
                           :step 78
                           }
                          {
                           :name "Germany"
                           :selectable true
                           :step 79
                           }
                          {
                           :name "Ghana"
                           :selectable true
                           :step 80
                           }
                          {
                           :name "Gibraltar"
                           :selectable true
                           :step 81
                           }
                          {
                           :name "Greece"
                           :selectable true
                           :step 82
                           }
                          {
                           :name "Greenland"
                           :selectable true
                           :step 83
                           }
                          {
                           :name "Grenada"
                           :selectable true
                           :step 84
                           }
                          {
                           :name "Guadeloupe"
                           :selectable true
                           :step 85
                           }
                          {
                           :name "Guam"
                           :selectable true
                           :step 86
                           }
                          {
                           :name "Guatemala"
                           :selectable true
                           :step 87
                           }
                          {
                           :name "Guernsey"
                           :selectable true
                           :step 88
                           }
                          {
                           :name "Guinea"
                           :selectable true
                           :step 89
                           }
                          {
                           :name "Guinea-Bissau"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Guyana"
                           :selectable true
                           :step 91
                           }
                          {
                           :name "Haiti"
                           :selectable true
                           :step 92
                           }
                          {
                           :name "Honduras"
                           :selectable true
                           :step 94
                           }
                          {
                           :name "Hong Kong"
                           :selectable true
                           :step 95
                           }
                          {
                           :name "Hungary"
                           :selectable true
                           :step 96
                           }
                          {
                           :name "Iceland"
                           :selectable true
                           :step 97
                           }
                          {
                           :name "India"
                           :selectable true
                           :step 98
                           }
                          {
                           :name "Indonesia"
                           :selectable true
                           :step 99
                           }
                          {
                           :name "Iran
Islamic Republic Of"
                           :selectable true
                           :step 100
                           }
                          {
                           :name "Iraq"
                           :selectable true
                           :step 101
                           }
                          {
                           :name "Ireland"
                           :selectable true
                           :step 102
                           }
                          {
                           :name "Israel"
                           :selectable true
                           :step 104
                           }
                          {
                           :name "Italy"
                           :selectable true
                           :step 105
                           }
                          {
                           :name "Jamaica"
                           :selectable true
                           :step 106
                           }
                          {
                           :name "Japan"
                           :selectable true
                           :step 107
                           }
                          {
                           :name "Jersey"
                           :selectable true
                           :step 108
                           }
                          {
                           :name "Jordan"
                           :selectable true
                           :step 109
                           }
                          {
                           :name "Kazakhstan"
                           :selectable true
                           :step 110
                           }
                          {
                           :name "Kenya"
                           :selectable true
                           :step 111
                           }
                          {
                           :name "Kuwait"
                           :selectable true
                           :step 113
                           }
                          {
                           :name "Kyrgyzstan"
                           :selectable true
                           :step 114
                           }
                          {
                           :name "Lao"
                           :selectable true
                           :step 115
                           }
                          {
                           :name "Latvia"
                           :selectable true
                           :step 116
                           }
                          {
                           :name "Lebanon"
                           :selectable true
                           :step 117
                           }
                          {
                           :name "Lesotho"
                           :selectable true
                           :step 118
                           }
                          {
                           :name "Liberia"
                           :selectable true
                           :step 119
                           }
                          {
                           :name "Libyan Arab Jamahiriya"
                           :selectable true
                           :step 120
                           }
                          {
                           :name "Liechtenstein"
                           :selectable true
                           :step 121
                           }
                          {
                           :name "Lithuania"
                           :selectable true
                           :step 122
                           }
                          {
                           :name "Luxembourg"
                           :selectable true
                           :step 123
                           }
                          {
                           :name "Macao"
                           :selectable true
                           :step 124
                           }
                          {
                           :name "Macedonia"
                           :selectable true
                           :step 125
                           }
                          {
                           :name "Madagascar"
                           :selectable true
                           :step 126
                           }
                          {
                           :name "Malawi"
                           :selectable true
                           :step 127
                           }
                          {
                           :name "Malaysia"
                           :selectable true
                           :step 128
                           }
                          {
                           :name "Maldives"
                           :selectable true
                           :step 129
                           }
                          {
                           :name "Mali"
                           :selectable true
                           :step 130
                           }
                          {
                           :name "Malta"
                           :selectable true
                           :step 131
                           }
                          {
                           :name "Martinique"
                           :selectable true
                           :step 133
                           }
                          {
                           :name "Mauritania"
                           :selectable true
                           :step 134
                           }
                          {
                           :name "Mauritius"
                           :selectable true
                           :step 135
                           }
                          {
                           :name "Mexico"
                           :selectable true
                           :step 137
                           }
                          {
                           :name "Micronesia"
                           :selectable true
                           :step 138
                           }
                          {
                           :name "Moldova"
                           :selectable true
                           :step 139
                           }
                          {
                           :name "Monaco"
                           :selectable true
                           :step 140
                           }
                          {
                           :name "Mongolia"
                           :selectable true
                           :step 141
                           }
                          {
                           :name "Montenegro"
                           :selectable true
                           :step 142
                           }
                          {
                           :name "Montserrat"
                           :selectable true
                           :step 143
                           }
                          {
                           :name "Morocco"
                           :selectable true
                           :step 144
                           }
                          {
                           :name "Mozambique"
                           :selectable true
                           :step 145
                           }
                          {
                           :name "Myanmar"
                           :selectable true
                           :step 146
                           }
                          {
                           :name "Namibia"
                           :selectable true
                           :step 147
                           }
                          {
                           :name "Nauru"
                           :selectable true
                           :step 148
                           }
                          {
                           :name "Nepal"
                           :selectable true
                           :step 149
                           }
                          {
                           :name "Netherlands"
                           :selectable true
                           :step 150
                           }
                          {
                           :name "New Caledonia"
                           :selectable true
                           :step 151
                           }
                          {
                           :name "New Zealand"
                           :selectable true
                           :step 152
                           }
                          {
                           :name "Nicaragua"
                           :selectable true
                           :step 153
                           }
                          {
                           :name "Niger"
                           :selectable true
                           :step 154
                           }
                          {
                           :name "Nigeria"
                           :selectable true
                           :step 155
                           }
                          {
                           :name "Niue"
                           :selectable true
                           :step 156
                           }
                          {
                           :name "Norfolk Island"
                           :selectable true
                           :step 157
                           }
                          {
                           :name "North Korea"
                           :selectable true
                           :step 158
                           }
                          {
                           :name "Norway"
                           :selectable true
                           :step 160
                           }
                          {
                           :name "Oman"
                           :selectable true
                           :step 161
                           }
                          {
                           :name "Pakistan"
                           :selectable true
                           :step 162
                           }
                          {
                           :name "Palau"
                           :selectable true
                           :step 163
                           }
                          {
                           :name "Palestine"
                           :selectable true
                           :step 164
                           }
                          {
                           :name "Panama"
                           :selectable true
                           :step 165
                           }
                          {
                           :name "Papua New Guinea"
                           :selectable true
                           :step 166
                           }
                          {
                           :name "Paraguay"
                           :selectable true
                           :step 167
                           }
                          {
                           :name "Peru"
                           :selectable true
                           :step 168
                           }
                          {
                           :name "Philippines"
                           :selectable true
                           :step 169
                           }
                          {
                           :name "Pitcairn"
                           :selectable true
                           :step 170
                           }
                          {
                           :name "Poland"
                           :selectable true
                           :step 171
                           }
                          {
                           :name "Portugal"
                           :selectable true
                           :step 172
                           }
                          {
                           :name "Puerto Rico"
                           :selectable true
                           :step 173
                           }
                          {
                           :name "Qatar"
                           :selectable true
                           :step 174
                           }
                          {
                           :name "Réunion"
                           :selectable true
                           :step 178
                           }
                          {
                           :name "Romania"
                           :selectable true
                           :step 175
                           }
                          {
                           :name "Russia"
                           :selectable true
                           :step 176
                           }
                          {
                           :name "Rwanda"
                           :selectable true
                           :step 177
                           }
                          {
                           :name "Samoa"
                           :selectable true
                           :step 186
                           }
                          {
                           :name "San Marino"
                           :selectable true
                           :step 187
                           }
                          {
                           :name "Saudi Arabia"
                           :selectable true
                           :step 189
                           }
                          {
                           :name "Senegal"
                           :selectable true
                           :step 190
                           }
                          {
                           :name "Serbia"
                           :selectable true
                           :step 191
                           }
                          {
                           :name "Seychelles"
                           :selectable true
                           :step 192
                           }
                          {
                           :name "Sierra Leone"
                           :selectable true
                           :step 193
                           }
                          {
                           :name "Singapore"
                           :selectable true
                           :step 194
                           }
                          {
                           :name "Slovakia"
                           :selectable true
                           :step 195
                           }
                          {
                           :name "Slovenia"
                           :selectable true
                           :step 196
                           }
                          {
                           :name "Solomon Islands"
                           :selectable true
                           :step 197
                           }
                          {
                           :name "Somalia"
                           :selectable true
                           :step 198
                           }
                          {
                           :name "South Africa"
                           :selectable true
                           :step 199
                           }
                          {
                           :name "South Korea"
                           :selectable true
                           :step 201
                           }
                          {
                           :name "Spain"
                           :selectable true
                           :step 202
                           }
                          {
                           :name "Sri Lanka"
                           :selectable true
                           :step 203
                           }
                          {
                           :name "Sudan"
                           :selectable true
                           :step 204
                           }
                          {
                           :name "Suriname"
                           :selectable true
                           :step 205
                           }
                          {
                           :name "Swaziland"
                           :selectable true
                           :step 207
                           }
                          {
                           :name "Sweden"
                           :selectable true
                           :step 208
                           }
                          {
                           :name "Switzerland"
                           :selectable true
                           :step 209
                           }
                          {
                           :name "Syrian Arab Republic"
                           :selectable true
                           :step 210
                           }
                          {
                           :name "Taiwan"
                           :selectable true
                           :step 211
                           }
                          {
                           :name "Tajikistan"
                           :selectable true
                           :step 212
                           }
                          {
                           :name "Tanzania"
                           :selectable true
                           :step 213
                           }
                          {
                           :name "Thailand"
                           :selectable true
                           :step 214
                           }
                          {
                           :name "Timor-Leste"
                           :selectable true
                           :step 215
                           }
                          {
                           :name "Togo"
                           :selectable true
                           :step 216
                           }
                          {
                           :name "Tokelau"
                           :selectable true
                           :step 217
                           }
                          {
                           :name "Tonga"
                           :selectable true
                           :step 218
                           }
                          {
                           :name "Trinidad And Tobago"
                           :selectable true
                           :step 219
                           }
                          {
                           :name "Tunisia"
                           :selectable true
                           :step 220
                           }
                          {
                           :name "Turkey"
                           :selectable true
                           :step 221
                           }
                          {
                           :name "Turkmenistan"
                           :selectable true
                           :step 222
                           }
                          {
                           :name "Uganda"
                           :selectable true
                           :step 225
                           }
                          {
                           :name "Ukraine"
                           :selectable true
                           :step 226
                           }
                          {
                           :name "United Arab Emirates"
                           :selectable true
                           :step 227
                           }
                          {
                           :name "United Kingdom"
                           :selectable true
                           :step 228
                           }
                          {
                           :name "United States of America"
                           :selectable true
                           :step 230
                           }
                          {
                           :name "Uruguay"
                           :selectable true
                           :step 231
                           }
                          {
                           :name "Uzbekistan"
                           :selectable true
                           :step 232
                           }
                          {
                           :name "Vanuatu"
                           :selectable true
                           :step 233
                           }
                          {
                           :name "Vatican City State"
                           :selectable true
                           :step 234
                           }
                          {
                           :name "Venezuela"
                           :selectable true
                           :step 235
                           }
                          {
                           :name "Vietnam"
                           :selectable true
                           :step 236
                           }
                          {
                           :name "Western Sahara"
                           :selectable true
                           :step 240
                           }
                          {
                           :name "Yemen"
                           :selectable true
                           :step 241
                           }
                          {
                           :name "Zambia"
                           :selectable true
                           :step 242
                           }
                          {
                           :name "Zimbabwe"
                           :selectable true
                           :step 243
                           } ]
     }
    {
     :name "publication type"
     :tree true
     :step 0
     :choiceGroupValues [ {
                           :name "Annotation"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 110
                           }
                          {
                           :name "Anthology"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 120
                           }
                          {
                           :name "Artistic/Performance"
                           :selectable false
                           :parentChoiceGroupValue "publication type"
                           :step 440
                           }
                          {
                           :name "Artwork"
                           :selectable true
                           :parentChoiceGroupValue "Artistic/Performance"
                           :step 505
                           }
                          {
                           :name "Book"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 130
                           }
                          {
                           :name "Book Chapter Abstract"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 140
                           }
                          {
                           :name "Book Chapter Review"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 150
                           }
                          {
                           :name "bookRelated"
                           :selectable false
                           :parentChoiceGroupValue "publication type"
                           :step 170
                           }
                          {
                           :name "Book review"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 160
                           }
                          {
                           :name "Commentary"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 180
                           }
                          {
                           :name "Composition"
                           :selectable true
                           :parentChoiceGroupValue "Artistic/Performance"
                           :step 515
                           }
                          {
                           :name "Conference proceeding"
                           :selectable true
                           :parentChoiceGroupValue "conferenceRelated"
                           :step 190
                           }
                          {
                           :name "Conference proceedings article"
                           :selectable true
                           :parentChoiceGroupValue "conferenceRelated"
                           :step 200
                           }
                          {
                           :name "conferenceRelated"
                           :selectable false
                           :parentChoiceGroupValue "publication type"
                           :step 150
                           }
                          {
                           :name "Doctoral thesis"
                           :selectable true
                           :parentChoiceGroupValue "Thesis"
                           :step 220
                           }
                          {
                           :name "Edited book"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 135
                           }
                          {
                           :name "Encyclopedia"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 230
                           }
                          {
                           :name "Inbook"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 240
                           }
                          {
                           :name "Journal article"
                           :selectable true
                           :parentChoiceGroupValue "journalRelated"
                           :step 250
                           }
                          {
                           :name "Journal Article Abstract"
                           :selectable true
                           :parentChoiceGroupValue "journalRelated"
                           :step 260
                           }
                          {
                           :name "Journal article review"
                           :selectable true
                           :parentChoiceGroupValue "journalRelated"
                           :step 270
                           }
                          {
                           :name "journalRelated"
                           :selectable false
                           :parentChoiceGroupValue "publication type"
                           :step 100
                           }
                          {
                           :name "Letter"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 290
                           }
                          {
                           :name "Letter to Editor"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 300
                           }
                          {
                           :name "Manual"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 310
                           }
                          {
                           :name "Monograph"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 320
                           }
                          {
                           :name "Newsclipping"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 330
                           }
                          {
                           :name "Other"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 340
                           }
                          {
                           :name "Otherbook"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 350
                           }
                          {
                           :name "Other Outputs"
                           :selectable false
                           :parentChoiceGroupValue "publication type"
                           :step 450
                           }
                          {
                           :name "Performance"
                           :selectable true
                           :parentChoiceGroupValue "Artistic/Performance"
                           :step 510
                           }
                          {
                           :name "PhD Thesis"
                           :selectable true
                           :parentChoiceGroupValue "Thesis"
                           :step 360
                           }
                          {
                           :name "Poster"
                           :selectable true
                           :parentChoiceGroupValue "conferenceRelated"
                           :step 370
                           }
                          {
                           :name "Presentation"
                           :selectable true
                           :parentChoiceGroupValue "conferenceRelated"
                           :step 380
                           }
                          {
                           :name "publication type"
                           :selectable false
                           :step 0
                           }
                          {
                           :name "Referencebook"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 390
                           }
                          {
                           :name "Report"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 400
                           }
                          {
                           :name "Short Communication"
                           :selectable true
                           :parentChoiceGroupValue "Other Outputs"
                           :step 410
                           }
                          {
                           :name "Textbook"
                           :selectable true
                           :parentChoiceGroupValue "bookRelated"
                           :step 420
                           }
                          {
                           :name "Thesis"
                           :selectable false
                           :parentChoiceGroupValue "publication type"
                           :step 430
                           } ]
     }
    {
     :name "publication status"
     :tree false
     :choiceGroupValues [ {
                           :name "Accepted"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "In press"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "In progress"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Published"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Submitted"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "currency"
     :tree false
     :choiceGroupValues [ {
                           :name "ADF"
                           :selectable true
                           :step 110
                           }
                          {
                           :name "ADP"
                           :selectable true
                           :step 120
                           }
                          {
                           :name "AED"
                           :selectable true
                           :step 130
                           }
                          {
                           :name "AFN"
                           :selectable true
                           :step 140
                           }
                          {
                           :name "ALL"
                           :selectable true
                           :step 150
                           }
                          {
                           :name "AMD"
                           :selectable true
                           :step 160
                           }
                          {
                           :name "ANG"
                           :selectable true
                           :step 170
                           }
                          {
                           :name "AOA"
                           :selectable true
                           :step 180
                           }
                          {
                           :name "ARS"
                           :selectable true
                           :step 190
                           }
                          {
                           :name "AUD"
                           :selectable true
                           :step 200
                           }
                          {
                           :name "AWG"
                           :selectable true
                           :step 210
                           }
                          {
                           :name "AZN"
                           :selectable true
                           :step 220
                           }
                          {
                           :name "BAM"
                           :selectable true
                           :step 230
                           }
                          {
                           :name "BBD"
                           :selectable true
                           :step 240
                           }
                          {
                           :name "BDT"
                           :selectable true
                           :step 250
                           }
                          {
                           :name "BGN"
                           :selectable true
                           :step 260
                           }
                          {
                           :name "BHD"
                           :selectable true
                           :step 270
                           }
                          {
                           :name "BIF"
                           :selectable true
                           :step 280
                           }
                          {
                           :name "BMD"
                           :selectable true
                           :step 290
                           }
                          {
                           :name "BND"
                           :selectable true
                           :step 300
                           }
                          {
                           :name "BOB"
                           :selectable true
                           :step 310
                           }
                          {
                           :name "BRL"
                           :selectable true
                           :step 320
                           }
                          {
                           :name "BSD"
                           :selectable true
                           :step 330
                           }
                          {
                           :name "BTN"
                           :selectable true
                           :step 340
                           }
                          {
                           :name "BWP"
                           :selectable true
                           :step 350
                           }
                          {
                           :name "BYR"
                           :selectable true
                           :step 360
                           }
                          {
                           :name "BZD"
                           :selectable true
                           :step 370
                           }
                          {
                           :name "CAD"
                           :selectable true
                           :step 380
                           }
                          {
                           :name "CDF"
                           :selectable true
                           :step 390
                           }
                          {
                           :name "CHF"
                           :selectable true
                           :step 400
                           }
                          {
                           :name "CLP"
                           :selectable true
                           :step 410
                           }
                          {
                           :name "CNY"
                           :selectable true
                           :step 420
                           }
                          {
                           :name "COP"
                           :selectable true
                           :step 430
                           }
                          {
                           :name "CRC"
                           :selectable true
                           :step 440
                           }
                          {
                           :name "CUP"
                           :selectable true
                           :step 450
                           }
                          {
                           :name "CVE"
                           :selectable true
                           :step 460
                           }
                          {
                           :name "CZK"
                           :selectable true
                           :step 470
                           }
                          {
                           :name "DJF"
                           :selectable true
                           :step 480
                           }
                          {
                           :name "DKK"
                           :selectable true
                           :step 490
                           }
                          {
                           :name "DOP"
                           :selectable true
                           :step 500
                           }
                          {
                           :name "DZD"
                           :selectable true
                           :step 510
                           }
                          {
                           :name "ECS"
                           :selectable true
                           :step 520
                           }
                          {
                           :name "EEK"
                           :selectable true
                           :step 530
                           }
                          {
                           :name "EGP"
                           :selectable true
                           :step 540
                           }
                          {
                           :name "ETB"
                           :selectable true
                           :step 550
                           }
                          {
                           :name "EUR"
                           :selectable true
                           :step 560
                           }
                          {
                           :name "FJD"
                           :selectable true
                           :step 570
                           }
                          {
                           :name "FKP"
                           :selectable true
                           :step 580
                           }
                          {
                           :name "GBP"
                           :selectable true
                           :step 590
                           }
                          {
                           :name "GEL"
                           :selectable true
                           :step 600
                           }
                          {
                           :name "GHS"
                           :selectable true
                           :step 610
                           }
                          {
                           :name "GIP"
                           :selectable true
                           :step 620
                           }
                          {
                           :name "GMD"
                           :selectable true
                           :step 630
                           }
                          {
                           :name "GNF"
                           :selectable true
                           :step 640
                           }
                          {
                           :name "GTQ"
                           :selectable true
                           :step 650
                           }
                          {
                           :name "GYD"
                           :selectable true
                           :step 660
                           }
                          {
                           :name "HKD"
                           :selectable true
                           :step 670
                           }
                          {
                           :name "HNL"
                           :selectable true
                           :step 680
                           }
                          {
                           :name "HRK"
                           :selectable true
                           :step 690
                           }
                          {
                           :name "HTG"
                           :selectable true
                           :step 700
                           }
                          {
                           :name "HUF"
                           :selectable true
                           :step 710
                           }
                          {
                           :name "IDR"
                           :selectable true
                           :step 720
                           }
                          {
                           :name "ILS"
                           :selectable true
                           :step 730
                           }
                          {
                           :name "INR"
                           :selectable true
                           :step 740
                           }
                          {
                           :name "IQD"
                           :selectable true
                           :step 750
                           }
                          {
                           :name "IRR"
                           :selectable true
                           :step 760
                           }
                          {
                           :name "ISK"
                           :selectable true
                           :step 770
                           }
                          {
                           :name "JMD"
                           :selectable true
                           :step 780
                           }
                          {
                           :name "JOD"
                           :selectable true
                           :step 790
                           }
                          {
                           :name "JPY"
                           :selectable true
                           :step 800
                           }
                          {
                           :name "KES"
                           :selectable true
                           :step 810
                           }
                          {
                           :name "KGS"
                           :selectable true
                           :step 820
                           }
                          {
                           :name "KHR"
                           :selectable true
                           :step 830
                           }
                          {
                           :name "KMF"
                           :selectable true
                           :step 840
                           }
                          {
                           :name "KPW"
                           :selectable true
                           :step 850
                           }
                          {
                           :name "KRW"
                           :selectable true
                           :step 860
                           }
                          {
                           :name "KWD"
                           :selectable true
                           :step 870
                           }
                          {
                           :name "KYD"
                           :selectable true
                           :step 880
                           }
                          {
                           :name "KZT"
                           :selectable true
                           :step 890
                           }
                          {
                           :name "LAK"
                           :selectable true
                           :step 900
                           }
                          {
                           :name "LBP"
                           :selectable true
                           :step 910
                           }
                          {
                           :name "LKR"
                           :selectable true
                           :step 920
                           }
                          {
                           :name "LRD"
                           :selectable true
                           :step 930
                           }
                          {
                           :name "LSL"
                           :selectable true
                           :step 940
                           }
                          {
                           :name "LTL"
                           :selectable true
                           :step 950
                           }
                          {
                           :name "LVL"
                           :selectable true
                           :step 960
                           }
                          {
                           :name "LYD"
                           :selectable true
                           :step 970
                           }
                          {
                           :name "MAD"
                           :selectable true
                           :step 980
                           }
                          {
                           :name "MDL"
                           :selectable true
                           :step 990
                           }
                          {
                           :name "MGF"
                           :selectable true
                           :step 1000
                           }
                          {
                           :name "MKD"
                           :selectable true
                           :step 1010
                           }
                          {
                           :name "MMK"
                           :selectable true
                           :step 1020
                           }
                          {
                           :name "MNT"
                           :selectable true
                           :step 1030
                           }
                          {
                           :name "MOP"
                           :selectable true
                           :step 1040
                           }
                          {
                           :name "MRO"
                           :selectable true
                           :step 1050
                           }
                          {
                           :name "MUR"
                           :selectable true
                           :step 1060
                           }
                          {
                           :name "MVR"
                           :selectable true
                           :step 1070
                           }
                          {
                           :name "MWK"
                           :selectable true
                           :step 1080
                           }
                          {
                           :name "MXN"
                           :selectable true
                           :step 1090
                           }
                          {
                           :name "MYR"
                           :selectable true
                           :step 1100
                           }
                          {
                           :name "MZN"
                           :selectable true
                           :step 1110
                           }
                          {
                           :name "NAD"
                           :selectable true
                           :step 1120
                           }
                          {
                           :name "NGN"
                           :selectable true
                           :step 1130
                           }
                          {
                           :name "NIO"
                           :selectable true
                           :step 1140
                           }
                          {
                           :name "NLG"
                           :selectable true
                           :step 1150
                           }
                          {
                           :name "NOK"
                           :selectable true
                           :step 1160
                           }
                          {
                           :name "NPR"
                           :selectable true
                           :step 1170
                           }
                          {
                           :name "NZD"
                           :selectable true
                           :step 1180
                           }
                          {
                           :name "OMR"
                           :selectable true
                           :step 1190
                           }
                          {
                           :name "PAB"
                           :selectable true
                           :step 1200
                           }
                          {
                           :name "PEN"
                           :selectable true
                           :step 1210
                           }
                          {
                           :name "PGK"
                           :selectable true
                           :step 1220
                           }
                          {
                           :name "PHP"
                           :selectable true
                           :step 1230
                           }
                          {
                           :name "PKR"
                           :selectable true
                           :step 1240
                           }
                          {
                           :name "PLN"
                           :selectable true
                           :step 1250
                           }
                          {
                           :name "PYG"
                           :selectable true
                           :step 1260
                           }
                          {
                           :name "QAR"
                           :selectable true
                           :step 1270
                           }
                          {
                           :name "RON"
                           :selectable true
                           :step 1280
                           }
                          {
                           :name "RSD"
                           :selectable true
                           :step 1290
                           }
                          {
                           :name "RUB"
                           :selectable true
                           :step 1300
                           }
                          {
                           :name "RWF"
                           :selectable true
                           :step 1310
                           }
                          {
                           :name "SAR"
                           :selectable true
                           :step 1320
                           }
                          {
                           :name "SBD"
                           :selectable true
                           :step 1330
                           }
                          {
                           :name "SCR"
                           :selectable true
                           :step 1340
                           }
                          {
                           :name "SDD"
                           :selectable true
                           :step 1350
                           }
                          {
                           :name "SDG"
                           :selectable true
                           :step 1360
                           }
                          {
                           :name "SDP"
                           :selectable true
                           :step 1370
                           }
                          {
                           :name "SEK"
                           :selectable true
                           :step 1380
                           }
                          {
                           :name "SGD"
                           :selectable true
                           :step 1390
                           }
                          {
                           :name "SHP"
                           :selectable true
                           :step 1400
                           }
                          {
                           :name "SIT"
                           :selectable true
                           :step 1410
                           }
                          {
                           :name "SKK"
                           :selectable true
                           :step 1420
                           }
                          {
                           :name "SLL"
                           :selectable true
                           :step 1430
                           }
                          {
                           :name "SOS"
                           :selectable true
                           :step 1440
                           }
                          {
                           :name "SRD"
                           :selectable true
                           :step 1450
                           }
                          {
                           :name "SRG"
                           :selectable true
                           :step 1460
                           }
                          {
                           :name "STD"
                           :selectable true
                           :step 1470
                           }
                          {
                           :name "SVC"
                           :selectable true
                           :step 1480
                           }
                          {
                           :name "SYP"
                           :selectable true
                           :step 1490
                           }
                          {
                           :name "SZL"
                           :selectable true
                           :step 1500
                           }
                          {
                           :name "THB"
                           :selectable true
                           :step 1510
                           }
                          {
                           :name "TJS"
                           :selectable true
                           :step 1520
                           }
                          {
                           :name "TMM"
                           :selectable true
                           :step 1530
                           }
                          {
                           :name "TND"
                           :selectable true
                           :step 1540
                           }
                          {
                           :name "TOP"
                           :selectable true
                           :step 1550
                           }
                          {
                           :name "TRY"
                           :selectable true
                           :step 1560
                           }
                          {
                           :name "TTD"
                           :selectable true
                           :step 1570
                           }
                          {
                           :name "TWD"
                           :selectable true
                           :step 1580
                           }
                          {
                           :name "TZS"
                           :selectable true
                           :step 1590
                           }
                          {
                           :name "UAH"
                           :selectable true
                           :step 1600
                           }
                          {
                           :name "UAK"
                           :selectable true
                           :step 1610
                           }
                          {
                           :name "UGX"
                           :selectable true
                           :step 1620
                           }
                          {
                           :name "USD"
                           :selectable true
                           :step 1630
                           }
                          {
                           :name "UYU"
                           :selectable true
                           :step 1640
                           }
                          {
                           :name "UZS"
                           :selectable true
                           :step 1650
                           }
                          {
                           :name "VEF"
                           :selectable true
                           :step 1660
                           }
                          {
                           :name "VND"
                           :selectable true
                           :step 1670
                           }
                          {
                           :name "VUV"
                           :selectable true
                           :step 1680
                           }
                          {
                           :name "WST"
                           :selectable true
                           :step 1690
                           }
                          {
                           :name "XAF"
                           :selectable true
                           :step 1700
                           }
                          {
                           :name "XAG"
                           :selectable true
                           :step 1710
                           }
                          {
                           :name "XAU"
                           :selectable true
                           :step 1720
                           }
                          {
                           :name "XOF"
                           :selectable true
                           :step 1730
                           }
                          {
                           :name "XPD"
                           :selectable true
                           :step 1740
                           }
                          {
                           :name "XPT"
                           :selectable true
                           :step 1750
                           }
                          {
                           :name "YUN"
                           :selectable true
                           :step 1760
                           }
                          {
                           :name "ZAR"
                           :selectable true
                           :step 1770
                           }
                          {
                           :name "ZMK"
                           :selectable true
                           :step 1780
                           }
                          {
                           :name "ZWD"
                           :selectable true
                           :step 1790
                           } ]
     }
    {
     :name "content type"
     :tree false
     :choiceGroupValues [ {
                           :name "Other scientific"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Peer reviewed"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Popular science"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "CONVERIS_MODULES"
     :tree false
     :choiceGroupValues [ {
                           :name "Annual Report"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Auto Relations Generator"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Choice Group Value Editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Citation retrieval"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "CV Generator"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Data Export"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Link Checker"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "List generator"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Member List"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "My Preferences"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Network Generator"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Project Report"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Publication Report"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Publication Search"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Review rejected publications"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Startpage Objects"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Static Content Editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Statistics"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Tree Editor"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Usage Statistics"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "User Delegation"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "User Management"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "Validate search profiles"
                           :selectable true
                           :step 0
                           }
                          {
                           :name "XML Data Import"
                           :selectable true
                           :step 0
                           } ]
     }
    {
     :name "language"
     :tree false
     :choiceGroupValues [ {
                           :name "Abkhazian"
                           :selectable true
                           :step 21
                           }
                          {
                           :name "Afar"
                           :selectable true
                           :step 22
                           }
                          {
                           :name "Afrikaans"
                           :selectable true
                           :step 23
                           }
                          {
                           :name "Akan"
                           :selectable true
                           :step 24
                           }
                          {
                           :name "Albanian"
                           :selectable true
                           :step 25
                           }
                          {
                           :name "Amharic"
                           :selectable true
                           :step 26
                           }
                          {
                           :name "Arabic"
                           :selectable true
                           :step 27
                           }
                          {
                           :name "Aragonese"
                           :selectable true
                           :step 28
                           }
                          {
                           :name "Armenian"
                           :selectable true
                           :step 29
                           }
                          {
                           :name "Assamese"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Avaric"
                           :selectable true
                           :step 31
                           }
                          {
                           :name "Avestan"
                           :selectable true
                           :step 32
                           }
                          {
                           :name "Aymara"
                           :selectable true
                           :step 33
                           }
                          {
                           :name "Azerbaijani"
                           :selectable true
                           :step 34
                           }
                          {
                           :name "Bambara"
                           :selectable true
                           :step 35
                           }
                          {
                           :name "Bashkir"
                           :selectable true
                           :step 36
                           }
                          {
                           :name "Basque"
                           :selectable true
                           :step 37
                           }
                          {
                           :name "Belarusian"
                           :selectable true
                           :step 38
                           }
                          {
                           :name "Bengali"
                           :selectable true
                           :step 39
                           }
                          {
                           :name "Bihari"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Bislama"
                           :selectable true
                           :step 41
                           }
                          {
                           :name "Bosnian"
                           :selectable true
                           :step 42
                           }
                          {
                           :name "Breton"
                           :selectable true
                           :step 43
                           }
                          {
                           :name "Bulgarian"
                           :selectable true
                           :step 44
                           }
                          {
                           :name "Burmese"
                           :selectable true
                           :step 45
                           }
                          {
                           :name "Catalan"
                           :selectable true
                           :step 46
                           }
                          {
                           :name "Central Khmer"
                           :selectable true
                           :step 47
                           }
                          {
                           :name "Chamorro"
                           :selectable true
                           :step 48
                           }
                          {
                           :name "Chechen"
                           :selectable true
                           :step 49
                           }
                          {
                           :name "Chinese"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Church Slavic"
                           :selectable true
                           :step 51
                           }
                          {
                           :name "Chuvash"
                           :selectable true
                           :step 52
                           }
                          {
                           :name "Cornish"
                           :selectable true
                           :step 53
                           }
                          {
                           :name "Corsican"
                           :selectable true
                           :step 54
                           }
                          {
                           :name "Cree"
                           :selectable true
                           :step 55
                           }
                          {
                           :name "Croatian"
                           :selectable true
                           :step 56
                           }
                          {
                           :name "Czech"
                           :selectable true
                           :step 57
                           }
                          {
                           :name "Danish"
                           :selectable true
                           :step 58
                           }
                          {
                           :name "Dhivehi"
                           :selectable true
                           :step 59
                           }
                          {
                           :name "Dutch"
                           :selectable true
                           :step 60
                           }
                          {
                           :name "Dzongkha"
                           :selectable true
                           :step 61
                           }
                          {
                           :name "English"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Esperanto"
                           :selectable true
                           :step 63
                           }
                          {
                           :name "Estonian"
                           :selectable true
                           :step 64
                           }
                          {
                           :name "Ewe"
                           :selectable true
                           :step 65
                           }
                          {
                           :name "Faroese"
                           :selectable true
                           :step 66
                           }
                          {
                           :name "Fijian"
                           :selectable true
                           :step 67
                           }
                          {
                           :name "Finnish"
                           :selectable true
                           :step 68
                           }
                          {
                           :name "Flemish"
                           :selectable true
                           :step 69
                           }
                          {
                           :name "French"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Fulah"
                           :selectable true
                           :step 71
                           }
                          {
                           :name "Gaelic"
                           :selectable true
                           :step 72
                           }
                          {
                           :name "Galician"
                           :selectable true
                           :step 73
                           }
                          {
                           :name "Ganda"
                           :selectable true
                           :step 74
                           }
                          {
                           :name "Georgian"
                           :selectable true
                           :step 75
                           }
                          {
                           :name "German"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Greek"
                           :selectable true
                           :step 77
                           }
                          {
                           :name "Greenlandic
Kalaallisut"
                           :selectable true
                           :step 78
                           }
                          {
                           :name "Guarani"
                           :selectable true
                           :step 79
                           }
                          {
                           :name "Gujarati"
                           :selectable true
                           :step 80
                           }
                          {
                           :name "Haitian"
                           :selectable true
                           :step 81
                           }
                          {
                           :name "Hausa"
                           :selectable true
                           :step 82
                           }
                          {
                           :name "Hebrew"
                           :selectable true
                           :step 83
                           }
                          {
                           :name "Herero"
                           :selectable true
                           :step 84
                           }
                          {
                           :name "Hindi"
                           :selectable true
                           :step 85
                           }
                          {
                           :name "Hiri Motu"
                           :selectable true
                           :step 86
                           }
                          {
                           :name "Hungarian"
                           :selectable true
                           :step 87
                           }
                          {
                           :name "Icelandic"
                           :selectable true
                           :step 88
                           }
                          {
                           :name "Ido"
                           :selectable true
                           :step 89
                           }
                          {
                           :name "Igbo"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Indonesian"
                           :selectable true
                           :step 91
                           }
                          {
                           :name "Interlingue"
                           :selectable true
                           :step 92
                           }
                          {
                           :name "Inuktitut"
                           :selectable true
                           :step 93
                           }
                          {
                           :name "Inupiaq"
                           :selectable true
                           :step 94
                           }
                          {
                           :name "Irish"
                           :selectable true
                           :step 95
                           }
                          {
                           :name "Italian"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Japanese"
                           :selectable true
                           :step 97
                           }
                          {
                           :name "Javanese"
                           :selectable true
                           :step 98
                           }
                          {
                           :name "Kannada"
                           :selectable true
                           :step 99
                           }
                          {
                           :name "Kanuri"
                           :selectable true
                           :step 100
                           }
                          {
                           :name "Kashmiri"
                           :selectable true
                           :step 101
                           }
                          {
                           :name "Kazakh"
                           :selectable true
                           :step 102
                           }
                          {
                           :name "Kikuyu"
                           :selectable true
                           :step 103
                           }
                          {
                           :name "Kinyarwanda"
                           :selectable true
                           :step 104
                           }
                          {
                           :name "Komi"
                           :selectable true
                           :step 105
                           }
                          {
                           :name "Kongo"
                           :selectable true
                           :step 106
                           }
                          {
                           :name "Korean"
                           :selectable true
                           :step 107
                           }
                          {
                           :name "Kuanyama"
                           :selectable true
                           :step 108
                           }
                          {
                           :name "Kurdish"
                           :selectable true
                           :step 109
                           }
                          {
                           :name "Kyrgyz"
                           :selectable true
                           :step 110
                           }
                          {
                           :name "Lao"
                           :selectable true
                           :step 111
                           }
                          {
                           :name "Latin"
                           :selectable true
                           :step 112
                           }
                          {
                           :name "Latvian"
                           :selectable true
                           :step 113
                           }
                          {
                           :name "Limburgan"
                           :selectable true
                           :step 114
                           }
                          {
                           :name "Lingala"
                           :selectable true
                           :step 115
                           }
                          {
                           :name "Lithuanian"
                           :selectable true
                           :step 116
                           }
                          {
                           :name "Luba-Katanga"
                           :selectable true
                           :step 117
                           }
                          {
                           :name "Luxembourgish"
                           :selectable true
                           :step 118
                           }
                          {
                           :name "Macedonian"
                           :selectable true
                           :step 119
                           }
                          {
                           :name "Malagasy"
                           :selectable true
                           :step 120
                           }
                          {
                           :name "Malay"
                           :selectable true
                           :step 121
                           }
                          {
                           :name "Malayalam"
                           :selectable true
                           :step 122
                           }
                          {
                           :name "Maldivian"
                           :selectable true
                           :step 123
                           }
                          {
                           :name "Maltese"
                           :selectable true
                           :step 124
                           }
                          {
                           :name "Manx"
                           :selectable true
                           :step 125
                           }
                          {
                           :name "Maori"
                           :selectable true
                           :step 126
                           }
                          {
                           :name "Marathi"
                           :selectable true
                           :step 127
                           }
                          {
                           :name "Marshallese"
                           :selectable true
                           :step 128
                           }
                          {
                           :name "Moldavian"
                           :selectable true
                           :step 129
                           }
                          {
                           :name "Mongolian"
                           :selectable true
                           :step 130
                           }
                          {
                           :name "Nauru"
                           :selectable true
                           :step 131
                           }
                          {
                           :name "Navaho"
                           :selectable true
                           :step 132
                           }
                          {
                           :name "Ndebele"
                           :selectable true
                           :step 133
                           }
                          {
                           :name "Ndonga"
                           :selectable true
                           :step 134
                           }
                          {
                           :name "Nepali"
                           :selectable true
                           :step 135
                           }
                          {
                           :name "Northern Sami"
                           :selectable true
                           :step 136
                           }
                          {
                           :name "Norwegian"
                           :selectable true
                           :step 137
                           }
                          {
                           :name "Nuosu"
                           :selectable true
                           :step 138
                           }
                          {
                           :name "Nyanja"
                           :selectable true
                           :step 139
                           }
                          {
                           :name "Occidental"
                           :selectable true
                           :step 140
                           }
                          {
                           :name "Occitan"
                           :selectable true
                           :step 141
                           }
                          {
                           :name "Ojibwa"
                           :selectable true
                           :step 142
                           }
                          {
                           :name "Oriya"
                           :selectable true
                           :step 143
                           }
                          {
                           :name "Oromo"
                           :selectable true
                           :step 144
                           }
                          {
                           :name "Ossetian"
                           :selectable true
                           :step 145
                           }
                          {
                           :name "Pali"
                           :selectable true
                           :step 146
                           }
                          {
                           :name "Panjabi"
                           :selectable true
                           :step 147
                           }
                          {
                           :name "Pashto"
                           :selectable true
                           :step 148
                           }
                          {
                           :name "Persian"
                           :selectable true
                           :step 149
                           }
                          {
                           :name "Polish"
                           :selectable true
                           :step 150
                           }
                          {
                           :name "Portuguese"
                           :selectable true
                           :step 151
                           }
                          {
                           :name "Quechua"
                           :selectable true
                           :step 152
                           }
                          {
                           :name "Romanian"
                           :selectable true
                           :step 153
                           }
                          {
                           :name "Romansh"
                           :selectable true
                           :step 154
                           }
                          {
                           :name "Rundi"
                           :selectable true
                           :step 155
                           }
                          {
                           :name "Russian"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Samoan"
                           :selectable true
                           :step 157
                           }
                          {
                           :name "Sango"
                           :selectable true
                           :step 158
                           }
                          {
                           :name "Sanskrit"
                           :selectable true
                           :step 159
                           }
                          {
                           :name "Sardinian"
                           :selectable true
                           :step 160
                           }
                          {
                           :name "Serbian"
                           :selectable true
                           :step 161
                           }
                          {
                           :name "Shona"
                           :selectable true
                           :step 162
                           }
                          {
                           :name "Sichuan Yi"
                           :selectable true
                           :step 163
                           }
                          {
                           :name "Sindhi"
                           :selectable true
                           :step 164
                           }
                          {
                           :name "Sinhalese"
                           :selectable true
                           :step 165
                           }
                          {
                           :name "Slovak"
                           :selectable true
                           :step 166
                           }
                          {
                           :name "Slovenian"
                           :selectable true
                           :step 167
                           }
                          {
                           :name "Somali"
                           :selectable true
                           :step 168
                           }
                          {
                           :name "Sotho
Southern"
                           :selectable true
                           :step 169
                           }
                          {
                           :name "Spanish"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Sundanese"
                           :selectable true
                           :step 171
                           }
                          {
                           :name "Swahili"
                           :selectable true
                           :step 172
                           }
                          {
                           :name "Swati"
                           :selectable true
                           :step 173
                           }
                          {
                           :name "Swedish"
                           :selectable true
                           :step 174
                           }
                          {
                           :name "Tagalog"
                           :selectable true
                           :step 175
                           }
                          {
                           :name "Tahitian"
                           :selectable true
                           :step 176
                           }
                          {
                           :name "Tajik"
                           :selectable true
                           :step 177
                           }
                          {
                           :name "Tamil"
                           :selectable true
                           :step 178
                           }
                          {
                           :name "Tatar"
                           :selectable true
                           :step 179
                           }
                          {
                           :name "Telugu"
                           :selectable true
                           :step 180
                           }
                          {
                           :name "Thai"
                           :selectable true
                           :step 181
                           }
                          {
                           :name "Tibetan"
                           :selectable true
                           :step 182
                           }
                          {
                           :name "Tigrinya"
                           :selectable true
                           :step 183
                           }
                          {
                           :name "Tonga (Tonga Islands)"
                           :selectable true
                           :step 184
                           }
                          {
                           :name "Tsonga"
                           :selectable true
                           :step 185
                           }
                          {
                           :name "Tswana"
                           :selectable true
                           :step 186
                           }
                          {
                           :name "Turkish"
                           :selectable true
                           :step 187
                           }
                          {
                           :name "Turkmen"
                           :selectable true
                           :step 188
                           }
                          {
                           :name "Twi"
                           :selectable true
                           :step 189
                           }
                          {
                           :name "Ukrainian"
                           :selectable true
                           :step 190
                           }
                          {
                           :name "Urdu"
                           :selectable true
                           :step 191
                           }
                          {
                           :name "Uyghur"
                           :selectable true
                           :step 192
                           }
                          {
                           :name "Uzbek"
                           :selectable true
                           :step 193
                           }
                          {
                           :name "Valencian"
                           :selectable true
                           :step 194
                           }
                          {
                           :name "Venda"
                           :selectable true
                           :step 195
                           }
                          {
                           :name "Vietnamese"
                           :selectable true
                           :step 196
                           }
                          {
                           :name "Volapük"
                           :selectable true
                           :step 197
                           }
                          {
                           :name "Walloon"
                           :selectable true
                           :step 198
                           }
                          {
                           :name "Welsh"
                           :selectable true
                           :step 199
                           }
                          {
                           :name "Western Frisian"
                           :selectable true
                           :step 200
                           }
                          {
                           :name "Wolof"
                           :selectable true
                           :step 201
                           }
                          {
                           :name "Xhosa"
                           :selectable true
                           :step 202
                           }
                          {
                           :name "Yiddish"
                           :selectable true
                           :step 203
                           }
                          {
                           :name "Yoruba"
                           :selectable true
                           :step 204
                           }
                          {
                           :name "Zhuang"
                           :selectable true
                           :step 205
                           }
                          {
                           :name "Zulu"
                           :selectable true
                           :step 206
                           } ]
     }
    {
     :name "yes or no"
     :tree false
     :choiceGroupValues [ {
                           :name "No"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Yes"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "external or internal"
     :tree false
     :choiceGroupValues [ {
                           :name "External"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Internal"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "function"
     :tree false
     :choiceGroupValues [ {
                           :name "Academic Council"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Academic Director"
                           :selectable true
                           :step 35
                           }
                          {
                           :name "Academic Senior Councilor"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Administration"
                           :selectable true
                           :step 22
                           }
                          {
                           :name "Advisor"
                           :selectable true
                           :step 26
                           }
                          {
                           :name "Assistant"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Bachelor"
                           :selectable true
                           :step 47
                           }
                          {
                           :name "Chancellor"
                           :selectable true
                           :step 42
                           }
                          {
                           :name "Chief Engineer"
                           :selectable true
                           :step 18
                           }
                          {
                           :name "Chief Executive"
                           :selectable true
                           :step 9
                           }
                          {
                           :name "Chief Undersecretary"
                           :selectable true
                           :step 28
                           }
                          {
                           :name "Contract teacher"
                           :selectable true
                           :step 13
                           }
                          {
                           :name "Coordination"
                           :selectable true
                           :step 25
                           }
                          {
                           :name "Dean"
                           :selectable true
                           :step 32
                           }
                          {
                           :name "Dean of Academic Affairs"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Director"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Director of Research"
                           :selectable true
                           :step 45
                           }
                          {
                           :name "Director of Studies"
                           :selectable true
                           :step 38
                           }
                          {
                           :name "Emeritus Professor"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Entrepreneur"
                           :selectable true
                           :step 29
                           }
                          {
                           :name "Faculty"
                           :selectable true
                           :step 36
                           }
                          {
                           :name "Graduate"
                           :selectable true
                           :step 48
                           }
                          {
                           :name "Guest Professorship"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Head of Product Development"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Head of Service"
                           :selectable true
                           :step 46
                           }
                          {
                           :name "Head of Unit"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Institute Management"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "Junior Professorship"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "Junior Research-Group Leader"
                           :selectable true
                           :step 27
                           }
                          {
                           :name "Manager"
                           :selectable true
                           :step 33
                           }
                          {
                           :name "Part-time Faculty"
                           :selectable true
                           :step 41
                           }
                          {
                           :name "President"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Professor"
                           :selectable true
                           :step 19
                           }
                          {
                           :name "Project Manager"
                           :selectable true
                           :step 39
                           }
                          {
                           :name "Pro-vice-chancellor"
                           :selectable true
                           :step 44
                           }
                          {
                           :name "Research Assistant"
                           :selectable true
                           :step 24
                           }
                          {
                           :name "Research Fellow"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Scientific Employee"
                           :selectable true
                           :step 23
                           }
                          {
                           :name "Secretary's Office"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Senior Assistant"
                           :selectable true
                           :step 17
                           }
                          {
                           :name "Senior Manager"
                           :selectable true
                           :step 34
                           }
                          {
                           :name "Temporary Faculty"
                           :selectable true
                           :step 37
                           }
                          {
                           :name "Vice-chancellor"
                           :selectable true
                           :step 43
                           }
                          {
                           :name "Vice President"
                           :selectable true
                           :step 31
                           } ]
     }
    {
     :name "CONVERIS_REPORTS"
     :tree false
     :choiceGroupValues [ {
                           :name "Bibliometric Report for one organisation"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Bibliometric Report for one person"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "project status"
     :tree false
     :choiceGroupValues [ {
                           :name "Abgeschlossen"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Bewilligt"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Laufend"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "funding categorisation"
     :tree false
     :choiceGroupValues [ {
                           :name "Bund"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Europäische Union"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Förderverein"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Industrie"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "International"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Land"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Stiftung"
                           :selectable true
                           :step 4
                           } ]
     }
    {
     :name "type of assessment"
     :tree false
     :choiceGroupValues [ {
                           :name "not surveyed"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "surveyed"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "unknown"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "salutation"
     :tree false
     :choiceGroupValues [ {
                           :name "Frau"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Herr"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "teaching level"
     :tree false
     :choiceGroupValues [ {
                           :name "Absolventenebene"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Doktorandenebene"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Masterebene"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Post-doctoral"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "person status"
     :tree false
     }
    {
     :name "type of lecture"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Konferenz (Hauptredner)"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Mündliche Präsentation"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Posterpräsentation"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "type of employment"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Praktikum"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Teilzeit"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Vollzeit"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "type of education"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "B.Sc."
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Diplom"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Dr."
                           :selectable true
                           :step 4
                           }
                          {
                           :name "M.Sc."
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "graduation type"
     :tree false
     :choiceGroupValues [ {
                           :name "Abschlussarbeit Bachelor"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Abschlussarbeit Magister"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Abschlussarbeit Master"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Diplomarbeit"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Dissertation"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Habilitation"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Studienarbeit"
                           :selectable true
                           :step 6
                           } ]
     }
    {
     :name "type of activity"
     :tree true
     :choiceGroupValues [ {
                           :name "Award"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 540
                           }
                          {
                           :name "Consulting"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 551
                           }
                          {
                           :name "Editorship"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 560
                           }
                          {
                           :name "Funding"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 610
                           }
                          {
                           :name "new38"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 570
                           }
                          {
                           :name "Other merits"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 900
                           }
                          {
                           :name "Professional membership"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 630
                           }
                          {
                           :name "Review duties"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 660
                           }
                          {
                           :name "Supervision"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 690
                           }
                          {
                           :name "Teaching"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 700
                           }
                          {
                           :name "type of activity"
                           :selectable false
                           :step 1
                           }
                          {
                           :name "Visits"
                           :selectable true
                           :parentChoiceGroupValue "type of activity"
                           :step 620
                           } ]
     }
    {
     :name "peerReviewed"
     :tree false
     :choiceGroupValues [ {
                           :name "Ja"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Nein"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Nicht bekannt"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "role"
     :tree false
     :choiceGroupValues [ {
                           :name "Ansprechpartner/in extern"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "FOR-Mitarbeiter/in"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "KHYS-Mitarbeiter/in"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "KIT-Admin"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "KIT-Mitarbeiter/in"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Stammdatenpflege"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Committee"
     :tree false
     :choiceGroupValues [ {
                           :name "Aufsichtsrat"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "CRYS"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "KIT-Senat"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Präsidium"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Programmleitung"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "WTR"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "Fakultaetsamt"
     :tree false
     :choiceGroupValues [ {
                           :name "CIO"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "Dekan/-in"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Fakultätsgeschäftsführer/-in"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Präsident/-in"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Prodekan/-in"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Prodekan/-in für Forschung"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Prorektor/-in"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Senatsmitglied"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Studiendekan/-in"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Vizepräsident/-in"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Vorstandsmitglied"
                           :selectable true
                           :step 9
                           } ]
     }
    {
     :name "Processing status"
     :tree false
     :choiceGroupValues [ {
                           :name "Angefragt"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Angezeigt"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Antrag erfasst"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Vorausgefüllt"
                           :selectable true
                           :step 4
                           } ]
     }
    {
     :name "type of merit"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Aufsichtsratsmitglied"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "cost category"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Material"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Personal"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Reisen"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "type of qualification"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Training"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Zertifikat"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "type of engagement"
     :tree false
     :choiceGroupValues [ {
                           :name "Andere"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Konferenz"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Priority"
     :tree false
     :choiceGroupValues [ {
                           :name "Hoch"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Niedrig"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "Validation by institution"
     :tree false
     :choiceGroupValues [ {
                           :name "Keine Validierung erforderlich"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Validierung erforderlich"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "IP management"
     :tree false
     :choiceGroupValues [ {
                           :name "Ich benötige keine Beratung zu IP"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Ich wünsche Beratung bezüglich IP"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Classification"
     :tree false
     :choiceGroupValues {
                         :name "Klasse 1"
                         :selectable true
                         :step 1
                         }
     }
    {
     :name "Cost center"
     :tree false
     }
    {
     :name "Cost type"
     :tree false
     :choiceGroupValues [ {
                           :name "Druckkosten"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "Eigenmittel"
                           :selectable true
                           :step 14
                           }
                          {
                           :name "Einnahmen"
                           :selectable true
                           :step 15
                           }
                          {
                           :name "Geräte"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Geschäftsbedarf"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "Investitionsmittel"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Literatur"
                           :selectable true
                           :step 13
                           }
                          {
                           :name "Mieten"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Overhead Mittel"
                           :selectable true
                           :step 17
                           }
                          {
                           :name "Patentierungsmittel"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Personalmittel"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Publikationsmittel"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Rechnerkosten"
                           :selectable true
                           :step 9
                           }
                          {
                           :name "Reisemittel"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Sachmittel"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Sonstige Mittel"
                           :selectable true
                           :step 16
                           }
                          {
                           :name "Verbrauchsmittel"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "funding type"
     :tree false
     :choiceGroupValues [ {
                           :name "Auftrag"
                           :selectable true
                           :step 140
                           }
                          {
                           :name "Forschungspreis"
                           :selectable true
                           :step 130
                           }
                          {
                           :name "Großgerät"
                           :selectable true
                           :step 200
                           }
                          {
                           :name "Mobilitätskosten"
                           :selectable true
                           :step 180
                           }
                          {
                           :name "Personalmittel"
                           :selectable true
                           :step 210
                           }
                          {
                           :name "Sachmittel"
                           :selectable true
                           :step 220
                           }
                          {
                           :name "Special transition flat rate"
                           :selectable true
                           :step 240
                           }
                          {
                           :name "Standard flat rate"
                           :selectable true
                           :step 190
                           }
                          {
                           :name "Stipendium"
                           :selectable true
                           :step 120
                           }
                          {
                           :name "Unterauftrag (SC)"
                           :selectable true
                           :step 230
                           }
                          {
                           :name "Vollkosten (FC)"
                           :selectable true
                           :step 150
                           }
                          {
                           :name "Zusatzkosten (AC)"
                           :selectable true
                           :step 160
                           }
                          {
                           :name "Zuschuss"
                           :selectable true
                           :step 110
                           }
                          {
                           :name "Zuwendung"
                           :selectable true
                           :step 170
                           } ]
     }
    {
     :name "Proceeding"
     :tree false
     :choiceGroupValues [ {
                           :name "Without proceeding"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "With proceeding"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Type of contribution"
     :tree false
     :choiceGroupValues [ {
                           :name "Poster"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Talk"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "Type of publication application"
     :tree false
     :choiceGroupValues [ {
                           :name "Article in journal"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Conference contribution"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "validation status researcher"
     :tree false
     :choiceGroupValues [ {
                           :name "Confirmed"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Rejected"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "HGF Structure"
     :tree false
     :choiceGroupValues [ {
                           :name "Programm"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Topic"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Vorhaben"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "Involvement"
     :tree false
     :choiceGroupValues [ {
                           :name "Ansprechpartner/in"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Associated Contractor"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Buchhaltung"
                           :selectable true
                           :step 9
                           }
                          {
                           :name "Contractor"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Coordinator"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Major Subcontractor"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Member"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Mentor/in"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "Projektleiter/in"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Sekretariat"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Sprecher/in"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "Technische Leitung"
                           :selectable true
                           :step 13
                           }
                          {
                           :name "Teilprojektleiter/in"
                           :selectable true
                           :step 7
                           } ]
     }
    {
     :name "Organisationstyp"
     :tree true
     :choiceGroupValues [ {
                           :name "Charity"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 24
                           }
                          {
                           :name "Department"
                           :selectable true
                           :parentChoiceGroupValue "Internal"
                           :step 14
                           }
                          {
                           :name "European Commission"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 27
                           }
                          {
                           :name "External"
                           :selectable false
                           :parentChoiceGroupValue "Type of Organisation"
                           :step 20
                           }
                          {
                           :name "Faculty"
                           :selectable true
                           :parentChoiceGroupValue "Internal"
                           :step 13
                           }
                          {
                           :name "Government department"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 26
                           }
                          {
                           :name "Group"
                           :selectable true
                           :parentChoiceGroupValue "Internal"
                           :step 16
                           }
                          {
                           :name "Higher Education Institution"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 23
                           }
                          {
                           :name "Hospital"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 22
                           }
                          {
                           :name "Industry"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 21
                           }
                          {
                           :name "Institute"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 28
                           }
                          {
                           :name "Internal"
                           :selectable false
                           :parentChoiceGroupValue "Type of Organisation"
                           :step 11
                           }
                          {
                           :name "Research council"
                           :selectable true
                           :parentChoiceGroupValue "External"
                           :step 25
                           }
                          {
                           :name "Type of Organisation"
                           :selectable false
                           :step 0
                           }
                          {
                           :name "University"
                           :selectable true
                           :parentChoiceGroupValue "Internal"
                           :step 12
                           } ]
     }
    {
     :name "funding instrument"
     :tree false
     :choiceGroupValues [ {
                           :name "Advanced Investigator Grant (AdG)"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Anbahnungsmaßnahmen"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Co-funding of regional
National and International Programmes (COFUND)"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Collaborative project (CP): largescale integrating project"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Collaborative project (CP): small or medium- scale focused research action"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Conferences and Traning Courses (SCF/LCF)"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Coordination Action (CA)"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Coordination and Support Action (CSA)"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Early Stage Research Training (EST)"
                           :selectable true
                           :step 9
                           }
                          {
                           :name "ERA-NET"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Exzellenzcluster"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "Exzellenzzentrum"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "Feasibility Studie (FYS)"
                           :selectable true
                           :step 13
                           }
                          {
                           :name "Forschergruppe (FOR)"
                           :selectable true
                           :step 14
                           }
                          {
                           :name "Graduiertenkolleg (GRK)"
                           :selectable true
                           :step 15
                           }
                          {
                           :name "Graduiertenschule"
                           :selectable true
                           :step 16
                           }
                          {
                           :name "Industry-Academia Partnerships and Pathways (IAPP)"
                           :selectable true
                           :step 17
                           }
                          {
                           :name "Informationsveranstaltung"
                           :selectable true
                           :step 18
                           }
                          {
                           :name "Initial Training Network (ITN)"
                           :selectable true
                           :step 19
                           }
                          {
                           :name "Integrated Project (IP)"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "International Incoming Fellowships (IIF)"
                           :selectable true
                           :step 21
                           }
                          {
                           :name "International Outgoing Fellowships for Career Development (IOF)"
                           :selectable true
                           :step 22
                           }
                          {
                           :name "International Reintegration Grants (IRG)"
                           :selectable true
                           :step 23
                           }
                          {
                           :name "Intra-European Fellowships for Career Development (ERG)"
                           :selectable true
                           :step 24
                           }
                          {
                           :name "Joint Technology Initiatives (JTI)"
                           :selectable true
                           :step 25
                           }
                          {
                           :name "Marie Curie actions (MCA)"
                           :selectable true
                           :step 26
                           }
                          {
                           :name "Nachwuchsgruppe"
                           :selectable true
                           :step 27
                           }
                          {
                           :name "Network of Excellence (NoE)"
                           :selectable true
                           :step 28
                           }
                          {
                           :name "Network of Excellent Retired Scientist (NES)"
                           :selectable true
                           :step 29
                           }
                          {
                           :name "New Field Group (NFG)"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Normalverfahren"
                           :selectable true
                           :step 31
                           }
                          {
                           :name "Preis"
                           :selectable true
                           :step 32
                           }
                          {
                           :name "Research Group (RG)"
                           :selectable true
                           :step 33
                           }
                          {
                           :name "Research Training Networks (RTN)"
                           :selectable true
                           :step 34
                           }
                          {
                           :name "Schwerpunktprogramm (SPP)"
                           :selectable true
                           :step 35
                           }
                          {
                           :name "Shared Professorship (SP)"
                           :selectable true
                           :step 36
                           }
                          {
                           :name "Shared Research Group (SRG)"
                           :selectable true
                           :step 37
                           }
                          {
                           :name "Sonderforschungsbereich (SFB)"
                           :selectable true
                           :step 38
                           }
                          {
                           :name "Specific Support Action (SSA)"
                           :selectable true
                           :step 39
                           }
                          {
                           :name "Specific Targeted Research Project (STREP)"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Starting Indipendent Researcher Grant (StG)"
                           :selectable true
                           :step 41
                           }
                          {
                           :name "Studie"
                           :selectable true
                           :step 42
                           }
                          {
                           :name "Transferbereich"
                           :selectable true
                           :step 44
                           }
                          {
                           :name "Transfer of Knowledge (TOK)"
                           :selectable true
                           :step 43
                           }
                          {
                           :name "Transregio (TR)"
                           :selectable true
                           :step 45
                           }
                          {
                           :name "Verbundforschung"
                           :selectable true
                           :step 46
                           }
                          {
                           :name "Young Investigator Group (YIG)"
                           :selectable true
                           :step 47
                           } ]
     }
    {
     :name "tender"
     :tree false
     :choiceGroupValues [ {
                           :name "Test Ausschreibung 1"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Test Ausschreibung 2"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "IOT_PUBLICATION_CLONING_VERSION"
     :tree false
     :choiceGroupValues [ {
                           :name "Major"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Minor"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Gender"
     :tree false
     :choiceGroupValues [ {
                           :name "female"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "male"
                           :selectable true
                           :step 11
                           } ]
     }
    {
     :name "type of task"
     :tree false
     :choiceGroupValues [ {
                           :name "Audit"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Payment"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Report"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "overall grade"
     :tree false
     :choiceGroupValues [ {
                           :name "Cum laude"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Magna cum laude"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Non rite"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Rite"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Satis bene"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Summa Cum Laude"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Translation"
     :tree false
     :choiceGroupValues [ {
                           :name "h"
                           :selectable true
                           :step 15
                           }
                          {
                           :name "m"
                           :selectable true
                           :step 12
                           }
                          {
                           :name "o"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "patent protection"
     :tree false
     :choiceGroupValues [ {
                           :name "IMA was already informed"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "No patent protection needed"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "Document type"
     :tree false
     :choiceGroupValues [ {
                           :name "Administrative"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Financial"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Legal"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Scientific"
                           :selectable true
                           :step 4
                           } ]
     }
    {
     :name "Business area"
     :tree false
     :choiceGroupValues [ {
                           :name "Informatik"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Telekommunikation"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "Gemeinkostenmodell"
     :tree false
     :choiceGroupValues [ {
                           :name "Overhead (Industrie)"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Programmpauschale (DFG)"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Projektpauschale (BMBF)"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Special transition flat rate (EU)"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Standard flat rate (EU)"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "progress"
     :tree false
     :choiceGroupValues [ {
                           :name "Excellent"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Good"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Poor"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Satisfactory"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "recommendation"
     :tree false
     :choiceGroupValues [ {
                           :name "Continue"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Revise"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Withdraw"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "Peer rating"
     :tree false
     :choiceGroupValues [ {
                           :name "1"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "2"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "3"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "4"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "U"
                           :selectable true
                           :step 5
                           } ]
     }
    {
     :name "staffCategory"
     :tree false
     :choiceGroupValues [ {
                           :name "A"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "C"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "REFReqOutputs"
     :tree false
     :choiceGroupValues [ {
                           :name "0"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "1"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "2"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "3"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "4"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "IR collection"
     :tree false
     :choiceGroupValues [ {
                           :name "35"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "37"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "38"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "39"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "40"
                           :selectable true
                           :step 5
                           } ]
     }
    {
     :name "validation status orgadmin"
     :tree false
     :choiceGroupValues [ {
                           :name "Confirmed"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Rejected"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "stage"
     :tree false
     :choiceGroupValues [ {
                           :name "Single stage application"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Stage 1"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Stage 2"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "university entrance qualification type"
     :tree false
     :choiceGroupValues [ {
                           :name "AHR 03 - Gymnasium"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "AHR 06 - Gesamtschule"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "AHR 35 - FH-Abschluß"
                           :selectable true
                           :step 14
                           }
                          {
                           :name "FHR 73 - Fachschule"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "FHR 78 - Sonstige Studienberechtigung"
                           :selectable true
                           :step 43
                           }
                          {
                           :name "FHR 96 - ohne Angabe"
                           :selectable true
                           :step 45
                           } ]
     }
    {
     :name "Doctorate"
     :tree false
     :choiceGroupValues [ {
                           :name "Dr.-Ing."
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Dr. jur."
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Dr. med."
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Dr. med. dent."
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Dr. phil."
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Dr. rer. biol. hum."
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Dr. rer. nat."
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Dr. rer. pol."
                           :selectable true
                           :step 8
                           }
                          {
                           :name "Dr. theol."
                           :selectable true
                           :step 9
                           } ]
     }
    {
     :name "LangSkillLevel"
     :tree false
     :choiceGroupValues [ {
                           :name "basic user (A1)"
                           :selectable true
                           :step 70
                           }
                          {
                           :name "basic user (A2)"
                           :selectable true
                           :step 60
                           }
                          {
                           :name "independent user (B1)"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "independent user (B2)"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "proficient user (C1)"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "proficient user (C2)"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "type of event"
     :tree false
     :choiceGroupValues [ {
                           :name "Conference"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Course"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Lecture"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Performance"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Training"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "Event in"
     :tree false
     :choiceGroupValues [ {
                           :name "international (Nicht-IT)"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "national (IT)"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Südtirol"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "role in conference"
     :tree false
     :choiceGroupValues [ {
                           :name "Key note speaker"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Listener"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Organizer"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Speaker"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "role in event"
     :tree false
     :choiceGroupValues [ {
                           :name "Listener"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Organizer"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Tutor"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "RDF Domain"
     :tree false
     :choiceGroupValues [ {
                           :name "A1 - Knowledge base"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "A2 - Cognitive abilities"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "A3 - Creativity"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "B1 - Personal qualities"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "B2 - Self-managemen"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "B3 -  Professional and career development"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "C1 - Professional conduct"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "C2 -  Research management"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "C3 - Finance
funding and resources"
                           :selectable true
                           :step 9
                           }
                          {
                           :name "D1 - Working with others"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "D2 - Communication and dissemination"
                           :selectable true
                           :step 11
                           }
                          {
                           :name "D3 -  Engagement and impact"
                           :selectable true
                           :step 12
                           } ]
     }
    {
     :name "fundType"
     :tree false
     :choiceGroupValues [ {
                           :name "Infrastructure"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Research Project"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Travel grant"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "intOrExt"
     :tree false
     :choiceGroupValues [ {
                           :name "external"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "internal"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "typeOfCard"
     :tree false
     :choiceGroupValues [ {
                           :name "external"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "internal"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "typeOfCreation"
     :tree false
     :choiceGroupValues [ {
                           :name "imported"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "manually created"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "piCV"
     :tree false
     :choiceGroupValues [ {
                           :name "Not applicable"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Yes"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "regulatoryRequirements"
     :tree false
     :choiceGroupValues [ {
                           :name "N/A"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Pending"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Yes"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "riskLevel"
     :tree false
     :choiceGroupValues [ {
                           :name "Minimal risk"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "More than minimal risk"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "ethicsType"
     :tree false
     :choiceGroupValues [ {
                           :name "Animal subjects"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Human subjects"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "contract type"
     :tree false
     :choiceGroupValues [ {
                           :name "Coordination Action (CA)"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Integrated Project (IP)"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Network of Excellence (NoE)"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Specific Support Action (SSA)"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Strategic Research Project (STREP)"
                           :selectable true
                           :step 4
                           } ]
     }
    {
     :name "activity type"
     :tree false
     :choiceGroupValues [ {
                           :name "Contract research"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Externally funded research"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Externally funded research education"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Faculty funded research"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Faculty funded research education"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Other externally funded contract research"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "Other grant research"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Quarters"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "award type"
     :tree true
     :step 2
     :choiceGroupValues [ {
                           :name "award type"
                           :selectable false
                           :step 0
                           }
                          {
                           :name "Charity funded"
                           :selectable true
                           :parentChoiceGroupValue "Research projects"
                           :step 110
                           }
                          {
                           :name "Clinical trial"
                           :selectable true
                           :parentChoiceGroupValue "award type"
                           :step 120
                           }
                          {
                           :name "Contract research"
                           :selectable true
                           :parentChoiceGroupValue "Research projects"
                           :step 130
                           }
                          {
                           :name "DFG"
                           :selectable true
                           :parentChoiceGroupValue "Standard project"
                           :step 140
                           }
                          {
                           :name "EC-funded projects"
                           :selectable false
                           :parentChoiceGroupValue "Research projects"
                           :step 150
                           }
                          {
                           :name "Fellowship"
                           :selectable true
                           :parentChoiceGroupValue "award type"
                           :step 160
                           }
                          {
                           :name "FP7"
                           :selectable true
                           :parentChoiceGroupValue "EC-funded projects"
                           :step 170
                           }
                          {
                           :name "Internally funded project"
                           :selectable true
                           :parentChoiceGroupValue "award type"
                           :step 180
                           }
                          {
                           :name "Knowledge Transfer Partnership (KTP)"
                           :selectable true
                           :parentChoiceGroupValue "award type"
                           :step 190
                           }
                          {
                           :name "Marie Currie"
                           :selectable true
                           :parentChoiceGroupValue "EC-funded projects"
                           :step 200
                           }
                          {
                           :name "RCUK"
                           :selectable true
                           :parentChoiceGroupValue "Standard project"
                           :step 210
                           }
                          {
                           :name "Research projects"
                           :selectable false
                           :parentChoiceGroupValue "award type"
                           :step 220
                           }
                          {
                           :name "Standard project"
                           :selectable false
                           :parentChoiceGroupValue "Research projects"
                           :step 230
                           }
                          {
                           :name "Studentship"
                           :selectable true
                           :parentChoiceGroupValue "award type"
                           :step 240
                           }
                          {
                           :name "Travel/Conference support"
                           :selectable true
                           :parentChoiceGroupValue "award type"
                           :step 250
                           } ]
     }
    {
     :name "application type"
     :tree true
     :choiceGroupValues [ {
                           :name "application type"
                           :selectable false
                           :step 0
                           }
                          {
                           :name "Clinical Trial"
                           :selectable true
                           :parentChoiceGroupValue "application type"
                           :step 9
                           }
                          {
                           :name "Contract Research"
                           :selectable true
                           :parentChoiceGroupValue "Research projects"
                           :step 5
                           }
                          {
                           :name "EC-funded projects"
                           :selectable false
                           :parentChoiceGroupValue "Research projects"
                           :step 2
                           }
                          {
                           :name "Fellowship"
                           :selectable true
                           :parentChoiceGroupValue "application type"
                           :step 11
                           }
                          {
                           :name "FP7"
                           :selectable true
                           :parentChoiceGroupValue "EC-funded projects"
                           :step 3
                           }
                          {
                           :name "Knowledge Transfer Partnership"
                           :selectable true
                           :parentChoiceGroupValue "application type"
                           :step 14
                           }
                          {
                           :name "Marie Currie"
                           :selectable true
                           :parentChoiceGroupValue "EC-funded projects"
                           :step 4
                           }
                          {
                           :name "Research projects"
                           :selectable false
                           :parentChoiceGroupValue "application type"
                           :step 1
                           }
                          {
                           :name "Standard"
                           :selectable true
                           :parentChoiceGroupValue "application type"
                           :step 585
                           }
                          {
                           :name "Studentship"
                           :selectable true
                           :parentChoiceGroupValue "application type"
                           :step 10
                           }
                          {
                           :name "Travel/Conference Support"
                           :selectable true
                           :parentChoiceGroupValue "application type"
                           :step 12
                           } ]
     }
    {
     :name "approved by reo"
     :tree false
     :choiceGroupValues [ {
                           :name "No"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Yes"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "typeOfProjAppl"
     :tree true
     :step 2
     :choiceGroupValues [ {
                           :name "Charity funded"
                           :selectable true
                           :parentChoiceGroupValue "Research projects"
                           :step 110
                           }
                          {
                           :name "Clinical trial"
                           :selectable true
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 120
                           }
                          {
                           :name "Contract research"
                           :selectable true
                           :parentChoiceGroupValue "Research projects"
                           :step 130
                           }
                          {
                           :name "DFG"
                           :selectable true
                           :parentChoiceGroupValue "Standard project"
                           :step 140
                           }
                          {
                           :name "EC-funded projects"
                           :selectable false
                           :parentChoiceGroupValue "Research projects"
                           :step 150
                           }
                          {
                           :name "Fellowship"
                           :selectable true
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 160
                           }
                          {
                           :name "FP7"
                           :selectable true
                           :parentChoiceGroupValue "EC-funded projects"
                           :step 170
                           }
                          {
                           :name "Internally funded project"
                           :selectable true
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 180
                           }
                          {
                           :name "Knowledge Transfer Partnership (KTP)"
                           :selectable true
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 190
                           }
                          {
                           :name "Marie Currie"
                           :selectable true
                           :parentChoiceGroupValue "EC-funded projects"
                           :step 200
                           }
                          {
                           :name "RCUK"
                           :selectable true
                           :parentChoiceGroupValue "Standard project"
                           :step 210
                           }
                          {
                           :name "Research projects"
                           :selectable false
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 220
                           }
                          {
                           :name "Standard project"
                           :selectable false
                           :parentChoiceGroupValue "Research projects"
                           :step 230
                           }
                          {
                           :name "Studentship"
                           :selectable true
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 240
                           }
                          {
                           :name "Travel/Conference support"
                           :selectable true
                           :parentChoiceGroupValue "typeOfProjAppl"
                           :step 250
                           }
                          {
                           :name "typeOfProjAppl"
                           :selectable false
                           :step 0
                           } ]
     }
    {
     :name "typeOfPerson"
     :tree false
     :choiceGroupValues [ {
                           :name "External"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Internal"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "version"
     :tree false
     :choiceGroupValues [ {
                           :name "0.1"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "1.0"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "embargo situation"
     :tree false
     :choiceGroupValues [ {
                           :name "embargo"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "noEmbargo"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "noEmbargoIPcampus"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "noEmbargoIPlibrary"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "noEmbargoRequest"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "marital status"
     :tree false
     :choiceGroupValues [ {
                           :name "divorced"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "married"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "single"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "widowed"
                           :selectable true
                           :step 4
                           } ]
     }
    {
     :name "fieldOfResp"
     :tree false
     :choiceGroupValues [ {
                           :name "Administration"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Production"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Research"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "levelOfEducation"
     :tree false
     :choiceGroupValues [ {
                           :name "Bachelor"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Graduate"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Master"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "resultType"
     :tree false
     :choiceGroupValues [ {
                           :name "Dataset"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Equipment"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "IPR"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Material"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Software"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "resultStatus"
     :tree false
     :choiceGroupValues [ {
                           :name " for licensing"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "for public use"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "for restricted use only"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "indiDecision"
     :tree false
     :choiceGroupValues [ {
                           :name "Invention claimed"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Invention relinquished"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "patentAppStatus"
     :tree false
     :choiceGroupValues [ {
                           :name "application prepared"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "application rejected"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "application resubmitted"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "application submitted"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "patent awarded"
                           :selectable true
                           :step 50
                           } ]
     }
    {
     :name "typeOfFile"
     :tree false
     :choiceGroupValues [ {
                           :name "Audio file"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Description"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Fulltext"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Image"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "Other file"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Video file"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "patentType"
     :tree false
     :choiceGroupValues [ {
                           :name "Design"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Plant"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Utility"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "patentStatus"
     :tree false
     :choiceGroupValues [ {
                           :name "patent awarded"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "patent expired"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "patent lapsed"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "patent renewed"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "Event type"
     :tree false
     :choiceGroupValues [ {
                           :name "Events with approval of participation"
                           :selectable true
                           :step 123
                           }
                          {
                           :name "Events without approval"
                           :selectable true
                           :step 124
                           } ]
     }
    {
     :name "reasons for the seminar"
     :tree false
     :choiceGroupValues [ {
                           :name "because it was obligatory"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "because it was recommended to me"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "out of curiosity"
                           :selectable true
                           :step 1
                           } ]
     }
    {
     :name "embargo"
     :tree false
     :choiceGroupValues [ {
                           :name "Embargo"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "No embargo"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "Grade"
     :tree false
     :choiceGroupValues [ {
                           :name "1"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "2"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "3"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "4"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "5"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "6"
                           :selectable true
                           :step 6
                           } ]
     }
    {
     :name "equipmentType"
     :tree false
     }
    {
     :name "facilityAccess"
     :tree false
     :choiceGroupValues [ {
                           :name "internal"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "open or restricted"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "typeOfUsage"
     :tree false
     :choiceGroupValues [ {
                           :name "Equipment"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Equipment and Service"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Service"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "levelofAccess"
     :tree false
     :choiceGroupValues [ {
                           :name "internal"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "open or restricted"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "facilityType"
     :tree false
     :choiceGroupValues [ {
                           :name "distributed"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "single-site"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "virtual"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "operationStatus"
     :tree false
     :choiceGroupValues [ {
                           :name "being upgraded"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "decommissioned"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "in construction"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "operational or being upgraded"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "useType"
     :tree false
     :choiceGroupValues [ {
                           :name "both"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "industry"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "research"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "checkPerc"
     :tree false
     :choiceGroupValues {
                         :name "correct"
                         :selectable true
                         :step 10
                         }
     }
    {
     :name "frequencyBills"
     :tree false
     :choiceGroupValues [ {
                           :name "half-yearly"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "monthly"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "once-only"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "quarterly"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "yearly"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "licenseType"
     :tree false
     :choiceGroupValues [ {
                           :name "exclusive"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "non-exclusive"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "open-source"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "yesNoError"
     :tree false
     :choiceGroupValues [ {
                           :name "Error"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "No"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Yes"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "billingMode"
     :tree false
     :choiceGroupValues [ {
                           :name "in advance"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "in arrears"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "stexExpression"
     :tree false
     :choiceGroupValues [ {
                           :name "Bilateral"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Multilateral"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Network"
                           :selectable true
                           :step 3
                           } ]
     }
    {
     :name "stexNetwork"
     :tree false
     :choiceGroupValues {
                         :name "IRUN"
                         :selectable true
                         :step 1
                         }
     }
    {
     :name "jointDegreeType"
     :tree false
     :choiceGroupValues [ {
                           :name "Dual degree"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "Joint degree"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "Multiple degree"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 4
                           } ]
     }
    {
     :name "artworkType"
     :tree false
     :choiceGroupValues [ {
                           :name "Artistic Exhibitions"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Curatorial/Museum Exhibitions"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Exhibition Catalogues"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "Light Design"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Radio/TV Program"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Set Design"
                           :selectable true
                           :step 35
                           }
                          {
                           :name "Sound Design"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Visual Artwork"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "yesNoNa"
     :tree false
     :choiceGroupValues [ {
                           :name "No"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Not applicable"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Yes"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "coopType"
     :tree true
     :choiceGroupValues [ {
                           :name "Affiliated Institutes"
                           :selectable true
                           :parentChoiceGroupValue "Internal Cooperations"
                           :step 120
                           }
                          {
                           :name "coopType"
                           :selectable false
                           :step 0
                           }
                          {
                           :name "Explicit Cooperations"
                           :selectable false
                           :parentChoiceGroupValue "coopType"
                           :step 50
                           }
                          {
                           :name "Institutional Cooperation"
                           :selectable true
                           :parentChoiceGroupValue "Explicit Cooperations"
                           :step 53
                           }
                          {
                           :name "Institutional Membership"
                           :selectable true
                           :parentChoiceGroupValue "Explicit Cooperations"
                           :step 60
                           }
                          {
                           :name "Interdisciplinary Centres"
                           :selectable true
                           :parentChoiceGroupValue "Internal Cooperations"
                           :step 110
                           }
                          {
                           :name "Internal Cooperations"
                           :selectable false
                           :parentChoiceGroupValue "coopType"
                           :step 100
                           }
                          {
                           :name "IRIS - Integrative Research Institute"
                           :selectable true
                           :parentChoiceGroupValue "Internal Cooperations"
                           :step 115
                           }
                          {
                           :name "Others"
                           :selectable true
                           :parentChoiceGroupValue "coopType"
                           :step 200
                           }
                          {
                           :name "Staff Exchange"
                           :selectable true
                           :parentChoiceGroupValue "coopType"
                           :step 150
                           } ]
     }
    {
     :name "stexType"
     :tree false
     :choiceGroupValues [ {
                           :name "Cotutelle(Binational promotion procedure)"
                           :selectable true
                           :step 60
                           }
                          {
                           :name "Doctoralexchange"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Double degree"
                           :selectable true
                           :step 70
                           }
                          {
                           :name "Employeeexchange"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Language course"
                           :selectable true
                           :step 100
                           }
                          {
                           :name "Others"
                           :selectable true
                           :step 110
                           }
                          {
                           :name "Researcher/Facultyexchange"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "(Research)Internships"
                           :selectable true
                           :step 80
                           }
                          {
                           :name "Studentexchange"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Summer/Winterschool/Intensiveprogram"
                           :selectable true
                           :step 90
                           }
                          {
                           :name "Trainingcooperation"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "statusDocument"
     :tree false
     :step 0
     :choiceGroupValues [ {
                           :name "Active"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Archived"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Expired"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Inactive"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "In preparation"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "expectations"
     :tree false
     :choiceGroupValues [ {
                           :name "Does not meet expectations"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Meets expectations"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Not applicable"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "employeeType"
     :tree false
     :choiceGroupValues [ {
                           :name "Probationary faculty"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Temporary faculty"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Tenured faculty"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Unclassified professional"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "reappointment"
     :tree false
     :choiceGroupValues [ {
                           :name "Reappointment not recommended"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Reappointment recommended"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Reappointment recommended
contingent upon funding"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Reviewed"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "modeOfUsage"
     :tree false
     :choiceGroupValues [ {
                           :name "extern commercial"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "extern research"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "intern"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "maintenance"
                           :selectable true
                           :step 50
                           } ]
     }
    {
     :name "coopNature"
     :tree false
     :choiceGroupValues [ {
                           :name "Fully completed"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "LOI"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "MoU"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "type of curriculum"
     :tree false
     :choiceGroupValues [ {
                           :name "Course development"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Program development"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "type of experience"
     :tree false
     :choiceGroupValues [ {
                           :name "Academic"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Non-Academic"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "exception reason"
     :tree false
     :choiceGroupValues [ {
                           :name "Journal does not offer an OA route"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Not applicable"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "OA type"
     :tree false
     :choiceGroupValues [ {
                           :name "Gold"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Green"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "OA licence type"
     :tree false
     :choiceGroupValues [ {
                           :name "Attribution CC BY"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Attribution-NoDerivs CC BY-ND"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Attribution-NonCommercial CC BY-NC"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Attribution-NonCommercial-NoDerivs CC BY-NC-ND"
                           :selectable true
                           :step 60
                           }
                          {
                           :name "Attribution-NonCommercial-ShareAlike  CC BY-NC-SA"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Attribution-ShareAlike CC BY-SA"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "compositionType"
     :tree false
     :step 15
     :choiceGroupValues [ {
                           :name "Choreography"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "Musical Compositions"
                           :selectable true
                           :step 2
                           } ]
     }
    {
     :name "performanceType"
     :tree false
     :step 10
     :choiceGroupValues [ {
                           :name "Audio Recording"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Musical Performances"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Performance Art"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Theatric"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Video Recordings"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "consultingType"
     :tree true
     :step 0
     :choiceGroupValues [ {
                           :name "Administration Service"
                           :selectable true
                           :parentChoiceGroupValue "consultingType"
                           :step 10
                           }
                          {
                           :name "Clinical Service"
                           :selectable true
                           :parentChoiceGroupValue "Professional Service"
                           :step 31
                           }
                          {
                           :name "Clinical Trial"
                           :selectable true
                           :parentChoiceGroupValue "Professional Service"
                           :step 32
                           }
                          {
                           :name "Community Service"
                           :selectable true
                           :parentChoiceGroupValue "consultingType"
                           :step 20
                           }
                          {
                           :name "consultingType"
                           :selectable false
                           :step 0
                           }
                          {
                           :name "Industry"
                           :selectable true
                           :parentChoiceGroupValue "Professional Service"
                           :step 33
                           }
                          {
                           :name "Professional Service"
                           :selectable false
                           :parentChoiceGroupValue "consultingType"
                           :step 30
                           }
                          {
                           :name "Professional Society"
                           :selectable true
                           :parentChoiceGroupValue "Professional Service"
                           :step 35
                           }
                          {
                           :name "Service on University Committee"
                           :selectable true
                           :parentChoiceGroupValue "Professional Service"
                           :step 37
                           }
                          {
                           :name "Strategic Initiative"
                           :selectable true
                           :parentChoiceGroupValue "Professional Service"
                           :step 36
                           } ]
     }
    {
     :name "period"
     :tree false
     :choiceGroupValues [ {
                           :name "Autumn"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "Spring"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Summer"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Winter"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "instructionMethod"
     :tree false
     :choiceGroupValues [ {
                           :name "Didactic"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Other"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Precepting"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "academicCalendar"
     :tree false
     :choiceGroupValues [ {
                           :name "Other"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Semesters"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "supervisionType"
     :tree false
     :choiceGroupValues [ {
                           :name "Advising"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Mentoring"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "advisingRole"
     :tree false
     :choiceGroupValues [ {
                           :name "Advisor"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Co-Advisor"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Preceptor"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Research Advisor"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "courseType"
     :tree false
     :choiceGroupValues [ {
                           :name "Continuing Education"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Graduate"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Professional"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Undergraduate"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "projectYear"
     :tree false
     :choiceGroupValues [ {
                           :name "1"
                           :selectable true
                           :step 1
                           }
                          {
                           :name "10"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "2"
                           :selectable true
                           :step 2
                           }
                          {
                           :name "3"
                           :selectable true
                           :step 3
                           }
                          {
                           :name "4"
                           :selectable true
                           :step 4
                           }
                          {
                           :name "5"
                           :selectable true
                           :step 5
                           }
                          {
                           :name "6"
                           :selectable true
                           :step 6
                           }
                          {
                           :name "7"
                           :selectable true
                           :step 7
                           }
                          {
                           :name "8"
                           :selectable true
                           :step 8
                           }
                          {
                           :name "9"
                           :selectable true
                           :step 9
                           } ]
     }
    {
     :name "className"
     :tree false
     :choiceGroupValues [ {
                           :name "CCCV Area of Research"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "CCCV Field of Application"
                           :selectable true
                           :step 50
                           }
                          {
                           :name "CCCV Research Discipline"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "CCCV Technological Application"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Research Areas"
                           :selectable true
                           :step 10
                           } ]
     }
    {
     :name "spinePoint"
     :tree false
     :choiceGroupValues [ {
                           :name "1"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "2"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "3"
                           :selectable true
                           :step 30
                           } ]
     }
    {
     :name "majorOrMinor"
     :tree false
     :choiceGroupValues [ {
                           :name "major"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "minor"
                           :selectable true
                           :step 20
                           } ]
     }
    {
     :name "typeOfAppointment"
     :tree false
     :choiceGroupValues [ {
                           :name "Probationary Faculty"
                           :selectable true
                           :step 30
                           }
                          {
                           :name "Temporary Faculty"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Tenured Faculty"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Unclassified professional"
                           :selectable true
                           :step 40
                           } ]
     }
    {
     :name "researcherStatus"
     :tree false
     :choiceGroupValues [ {
                           :name "Doctoral Student"
                           :selectable true
                           :step 10
                           }
                          {
                           :name "Permanent faculty"
                           :selectable true
                           :step 40
                           }
                          {
                           :name "Post-doctoral student"
                           :selectable true
                           :step 20
                           }
                          {
                           :name "Temporary faculty"
                           :selectable true
                           :step 30
                           } ]
     } ]


)


